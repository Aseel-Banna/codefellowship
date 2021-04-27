package com.example.codefellowship;

import javax.persistence.*;

@Entity
public class UserFollowers {
    public UserFollowers(ApplicationUser applicationUser, ApplicationUser applicationUserFollower) {
        this.applicationUser = applicationUser;
        this.applicationUserFollower = applicationUserFollower;
    }

    public UserFollowers() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }


    public ApplicationUser getApplicationUserFollower() {
        return applicationUserFollower;
    }


    @ManyToOne
    @JoinColumn(name = "user_id")
    ApplicationUser applicationUser;


    @ManyToOne
    @JoinColumn(name = "follower_id")
    ApplicationUser applicationUserFollower;

    @Override
    public String toString() {
        return "UsersFollowers{" +
                "id=" + id +
                ", applicationUser=" + applicationUser +
                ", applicationUserFollower=" + applicationUserFollower +
                '}';
    }
}