package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({
    @NamedQuery(
            name="getMyFollow_id",
            query="SELECT e FROM Follow e WHERE e.followee = :followee_id AND e.follower = :follower_id"
            ),
    //テーブル内のフォローされているIDとGETでJSPからURL送信されたIDの一致
    //テーブル内のログインIDと現在ログインしているIDの一致
    @NamedQuery(
            name="getFollowlist_id",
            query="SELECT e FROM Follow e WHERE e.follower = :follower_id"
            )
  //テーブル内のログインIDと現在ログインしているIDの一致
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //フォローされている人のID情報(ログインID)
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Employee follower;

    //フォローしている人のID情報
    @ManyToOne
    @JoinColumn(name = "followee_id", nullable = false)
    private Employee followee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer follow_id) {
        this.id = follow_id;
    }

    public Employee getFollower() {
        return follower;
    }

    public void setFollower(Employee follower) {
        this.follower = follower;
    }

    public Employee getFollowee() {
        return followee;
    }

    public void setFollowee(Employee followee) {
        this.followee = followee;
    }

}