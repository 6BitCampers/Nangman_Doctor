package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginDto.*;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginMapper.userEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class customOAuth2UserService extends DefaultOAuth2UserService {

    private final userEntityMapper userEntityMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String role = "ROLE_LOGINONLY";
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrantionId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        userEntity userEntity = null;

        if (registrantionId.equals("naver")) { //naver
            oAuth2Response = new naverReponse(oAuth2User.getAttributes());

            userEntity = new userEntity().builder()
                    .user_email(oAuth2Response.getEmail())
                    .user_name(oAuth2Response.getName())
                    .user_role(role)
                    .user_gender(((naverReponse)oAuth2Response).getGender())
                    .user_hp(((naverReponse)oAuth2Response).getMobile())
                    .user_age(((naverReponse)oAuth2Response).getAge())
                    .user_nickname(((naverReponse)oAuth2Response).getNickname())
                    .user_type(registrantionId)
                    .build();

        } else if (registrantionId.equals("google")) { //구글
            oAuth2Response = new googleResponse(oAuth2User.getAttributes());

            userEntity = new userEntity().builder()
                    .user_email(oAuth2Response.getEmail())
                    .user_name(((googleResponse) oAuth2Response).getFamilyName()+((googleResponse) oAuth2Response).getGivenName())
                    .user_role(role)
                    .user_nickname(oAuth2Response.getName().split(" ")[1].replace("(","").replace(")",""))
                    .user_type(registrantionId)
                    .build();
        } else if (registrantionId.equals("kakao")) {
            oAuth2Response = new kakaoResponse(oAuth2User.getAttributes());

            userEntity = new userEntity().builder()
                    .user_email(oAuth2Response.getEmail())
                    .user_name(oAuth2Response.getName())
                    .user_role(role)
                    .user_nickname(((kakaoResponse) oAuth2Response).getNickname())
                    .user_age(((kakaoResponse) oAuth2Response).getAge())
                    .user_gender(((kakaoResponse) oAuth2Response).getGender().equals("mele")?"M":"F")
                    .user_hp("0"+((kakaoResponse) oAuth2Response).getPhone_number().split(" ")[1])
                    .user_type(registrantionId)
                    .build();

        } else {
            return null;
        }

        boolean emailcheck = userEntityMapper.findByEmail(userEntity) == 0;
        boolean emailcheck_em = userEntityMapper.findEmployeeByEmail(userEntity) == 0;

        if (emailcheck && emailcheck_em) {
            //insert
            userEntityMapper.insertNaverUser(userEntity);
            return new CustomUserDetails(userEntity, role);

        }
        else {
            //update
            if (!emailcheck) {
                userEntityMapper.updateNaverUser(userEntity);
                return new CustomUserDetails(userEntity,userEntityMapper.findRoleByUserEmail(userEntity));
            }
            else if (!emailcheck_em) {
                userEntityMapper.updateEmployeeUser(userEntity);
                return new CustomUserDetails(userEntity,userEntityMapper.findRoleByEmployeeEmail(userEntity));
            }
        }
        return null;
    }
}
