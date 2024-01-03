package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@ToString
public class CustomUserDetails implements UserDetails {
    private String id;
    private String email;
    private String pw;
    private String nickname;
    private String name;
    private String role;
    private boolean enabled;



    private Member loggedMember;

    public CustomUserDetails(Member loggedMember) {
        this.loggedMember = loggedMember;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return loggedMember.getRole().toString();
            }
        });
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
