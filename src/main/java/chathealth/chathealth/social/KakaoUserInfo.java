package chathealth.chathealth.social;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoUserInfo implements SocialUserInfo{
    private final Map<String , Object> attibutes;
    @Override
    public String getProvider() {return "kakao";}
    @Override
    public String getProviderId() {
        return getProvider()+"_"+attibutes.get("id");
    }

    @Override
    public String getEmail() {
        return "이메일 등록해주세요.";
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map)attibutes.get("properties");
        return (String)properties.get("nickname");
    }
}
