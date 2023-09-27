package com.example.practice.enity;

import com.example.practice.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String email;
    private int age;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(cascade = {CascadeType.DETACH,  CascadeType.MERGE})
    private List<Post>posts=new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private List<User> follows;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<User> followers;
    @ManyToMany(cascade = {CascadeType.DETACH,  CascadeType.MERGE})
    private List<User> friends;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public void addPost(Post post){
        this.posts.add(post);
    }
    public void removePost(Post post) {
        this.posts.remove(post);
    }
    public void addToFollows(User user){
        this.follows.add(user);
    }
    public void removeFromFollows(User user) {

        this.follows.remove(user);
    }
    public void addToFollowers(User user){
        this.followers.add(user);
    }
    public void removeFromFollowers(User user) {

        this.followers.remove(user);
    }

    public void addToFriends(User user){
        this.friends.add(user);
    }
    public void removeFromFriends(User user) {
        this.friends.remove(user);
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword(){
        return password;

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
