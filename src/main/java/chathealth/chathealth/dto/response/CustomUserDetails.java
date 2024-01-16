package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class CustomUserDetails implements UserDetails, OAuth2User {
    //private String id;
    private String email;
    private String pw;
    private String nickname;
    private String name;
    private String role;
    private boolean enabled;



    private Member loggedMember;
    private Map<String, Object> attributes;

    public CustomUserDetails(Member loggedMember) {
        this.loggedMember = loggedMember;
    }

    public CustomUserDetails(Member loggedMember, Map<String,Object> attributes) {
        this.loggedMember = loggedMember;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add((GrantedAuthority) () -> loggedMember.getRole().toString());
        return auth;
    }

    @Override
    public String getPassword() {
        return loggedMember.getPw();
    }

    @Override
    public String getUsername() {
        return loggedMember.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
