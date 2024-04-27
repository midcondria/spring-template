package com.commerce.team.chat.repository;

import com.commerce.team.chat.dto.ChatRoomListResponse;
import com.commerce.team.chat.dto.ProductInfo;
import com.commerce.team.chat.dto.UserInfo;
import com.commerce.team.user.domain.QUser;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.commerce.team.chat.domain.QChatRoom.chatRoom;
import static com.commerce.team.product.QProduct.product;
import static com.commerce.team.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatRoomListResponse> getMyRoomList(Long userId) {
        return jpaQueryFactory
            .select(Projections.constructor(ChatRoomListResponse.class,
                chatRoom.id.as("roomId"),
                Projections.constructor(UserInfo.class,
                    user.id,
                    user.name.as("nickname"),
                    user.email.as("profileImage")),
                Projections.constructor(ProductInfo.class,
                    product.id,
                    product.price,
                    product.name,
                    product.imageThumbnail,
                    product.saleState,
                    product.purchaser)
            ))
            .from(chatRoom)
            .join(user).on(user.id.eq(
                new CaseBuilder()
                    .when(chatRoom.sellerId.eq(userId)).then(chatRoom.buyerId)
                    .otherwise(chatRoom.sellerId)
            ))
            .where(chatRoom.buyerId.eq(userId).or(chatRoom.sellerId.eq(userId)))
            .join(product).on(product.id.eq(chatRoom.productId))
            .limit(5L)
            .fetch();
    }
}
