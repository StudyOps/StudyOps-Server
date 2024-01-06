package com.StudyOps.security.service;

import com.StudyOps.domain.user.dto.EndUserEmailDto;
import com.StudyOps.domain.user.dto.EndUserRequestDto;
import com.StudyOps.domain.user.entity.EndUser;
import com.StudyOps.domain.user.repository.EndUserRepository;
import com.StudyOps.security.dto.AuthorizationCodeDto;
import com.StudyOps.security.dto.TokenDto;
import com.StudyOps.security.dto.TokenResDto;
import com.StudyOps.security.entity.KakaoUserInfo;
import com.StudyOps.security.entity.RefreshToken;
import com.StudyOps.global.common.exception.CustomRuntimeException;
import com.StudyOps.security.jwt.TokenProvider;
import com.StudyOps.security.repository.RefreshTokenRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final EndUserRepository endUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public EndUserEmailDto signup(EndUserRequestDto endUserRequestDto) {
        if (endUserRepository.existsByEmail(endUserRequestDto.getEmail())) {
            throw new CustomRuntimeException("이미 가입되어 있는 유저입니다.", HttpStatus.BAD_REQUEST);
        }
        if(endUserRepository.existsByNickname(endUserRequestDto.getNickName())) {
            throw new CustomRuntimeException("이미 사용중인 닉네임 입니다.", HttpStatus.CONFLICT);
        }

        EndUser endUser = endUserRequestDto.toEndUser(passwordEncoder);
        return EndUserEmailDto.of(endUserRepository.save(endUser));
    }

    @Transactional
    public TokenResDto independentLogin(EndUserRequestDto endUserRequestDto, HttpServletResponse response) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = endUserRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. Refresh Token 쿠키 생성
        Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDto.getRefreshToken().getValue());
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        refreshTokenCookie.setHttpOnly(true);  //httponly 옵션 설정
        refreshTokenCookie.setSecure(true); //https 옵션 설정
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);


        // 5.  Refresh Token 저장
        String key = tokenDto.getRefreshToken().getKey();

        if(refreshTokenRepository.findByKey(key).isPresent())
            refreshTokenRepository.deleteByKey(key);

        refreshTokenRepository.save(tokenDto.getRefreshToken());

        // 6. 토큰 발급
        return tokenDto.getTokenResDto();
    }

    @Transactional
    public TokenResDto reissue(String accessToken, String refreshTokenReq, HttpServletResponse response) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(refreshTokenReq)) {
            throw new CustomRuntimeException("Refresh Token 이 유효하지 않습니다.", HttpStatus.BAD_REQUEST );
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new CustomRuntimeException("로그아웃 된 사용자입니다.",HttpStatus.BAD_REQUEST));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(refreshTokenReq)) {
            throw new CustomRuntimeException("토큰의 유저 정보가 일치하지 않습니다.",HttpStatus.BAD_REQUEST);
        }

        // 5. 새로운 Access 토큰 생성
        TokenResDto tokenResDto = tokenProvider.generateNewAccessToken(authentication);

        // 토큰 발급
        return tokenResDto;
    }
    @Transactional
    public TokenResDto socialLogin(AuthorizationCodeDto authorizationCodeDto,HttpServletResponse res) {
        String authorizationCode = authorizationCodeDto.getAuthorizationCode();
        String accessToken = getAccessToken(authorizationCode);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt = new RestTemplate();
        HttpEntity <MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = rt. exchange(
                "https://kapi.kakao.com/v2/user/me",
                 HttpMethod. POST,
                kakaoProfileRequest,
                String. class
                );

        KakaoUserInfo kakaoUserInfo = extractKakaoUserInfo(response.getBody());

        Long kakaoId = kakaoUserInfo.getId();
        String email = kakaoUserInfo.getEmail();

        String userName = email + ".kakao";
        String password = kakaoId.toString();

        EndUser user = endUserRepository.findByEmail(userName).orElse(null);

        if(user == null){
            EndUser saveUser = EndUserRequestDto.builder()
                    .nickName(userName)
                    .email(userName)
                    .password(password)
                    .build().toEndUser(passwordEncoder);
            endUserRepository.save(saveUser);
            EndUser findUser = endUserRepository.findByEmail(userName).get();
            findUser.changeNickName("user" + saveUser.getId().toString());
            return independentLogin(EndUserRequestDto.builder()
                    .email(findUser.getEmail())
                    .nickName(findUser.getNickname())
                    .password(password)
                    .build(),res);
        }
        return independentLogin(EndUserRequestDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickname())
                .password(password)
                .build(),res);
    }
    private KakaoUserInfo extractKakaoUserInfo(String responseBody){
        try {
            // JSON 파싱을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // ResponseEntity의 바디에서 JSON 노드 가져오기
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            Long id = jsonNode.get("id").asLong();
            String email = jsonNode.get("kakao_account").asText();
            return KakaoUserInfo.builder().id(id).email(email).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    private String getAccessToken(String authorizationCode){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id","177781a8cf213cdbe722ea4fa86540ff");
        params.add("redirect_uri", "http://localhost:3000/auth");
        params.add("code", authorizationCode);

        RestTemplate rt = new RestTemplate();
        HttpEntity <MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod. POST,
                kakaoTokenRequest, String. class
        );
        return extractAccessToken(response.getBody());
    }
    private String extractAccessToken(String responseBody) {
        try {
            // JSON 파싱을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // ResponseEntity의 바디에서 JSON 노드 가져오기
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // "access_token" 필드 값 가져오기
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}