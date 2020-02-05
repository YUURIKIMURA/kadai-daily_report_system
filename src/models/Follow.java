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
            )
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /*
    //reportsテーブル
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    */

    //フォローしている人のID情報
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Employee follower;
    //フォローされている人のID情報(ログインID)
    @ManyToOne
    @JoinColumn(name = "followee_id", nullable = false)
    private Employee followee;



    public Integer getid() {
        return id;
    }

    public void setid(Integer follow_id) {
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