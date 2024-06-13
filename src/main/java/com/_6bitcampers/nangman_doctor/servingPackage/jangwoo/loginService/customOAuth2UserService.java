package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.loginService;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.loginDto.OAuth2Response;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.loginDto.customOAuth2User;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.loginDto.googleResponse;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.loginDto.naverReponse;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.loginEntity.userEntity;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.loginMapper.userEntityMapper;
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
        System.out.println(oAuth2User.getAttributes());

        String registrantionId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        userEntity userEntity = null;

        if (registrantionId.equals("naver")) { //naver
            oAuth2Response = new naverReponse(oAuth2User.getAttributes());

            userEntity = new userEntity().builder()
                    .normal_user_email(oAuth2Response.getEmail())
                    .normal_user_name(oAuth2Response.getName())
                    .normal_user_role(role)
                    .normal_user_gender(((naverReponse)oAuth2Response).getGender())
                    .normal_user_hp(((naverReponse)oAuth2Response).getMobile())
                    .normal_user_age(((naverReponse)oAuth2Response).getAge())
                    .normal_user_nickname(((naverReponse)oAuth2Response).getNickname())
                    .build();

        } else if (registrantionId.equals("google")) { //구글
            oAuth2Response = new googleResponse(oAuth2User.getAttributes());

            userEntity = new userEntity().builder()
                    .normal_user_email(oAuth2Response.getEmail())
                    .normal_user_name(((googleResponse) oAuth2Response).getFamilyName()+((googleResponse) oAuth2Response).getGivenName())
                    .normal_user_nickname(oAuth2User.getName())
                    .normal_user_role(role)
                    .build();
        } else {
            return null;
        }

        boolean emailcheck = userEntityMapper.findByEmail(oAuth2Response.getEmail()) == 0;

        if (emailcheck) {
            //insert
            userEntityMapper.insertNaverUser(userEntity);
            return new customOAuth2User(oAuth2Response, role);

        }
        else {
            //update
            userEntityMapper.updateNaverUser(userEntity);
            return new customOAuth2User(oAuth2Response,userEntityMapper.findRoleByEmail(oAuth2Response.getEmail()));
        }
    }
}
