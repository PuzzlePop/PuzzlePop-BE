package com.ssafy.puzzlepop.useritem.domain;


import com.ssafy.puzzlepop.item.domain.Item;
import com.ssafy.puzzlepop.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateItem(Item item) {
        this.item = item;
    }
}
