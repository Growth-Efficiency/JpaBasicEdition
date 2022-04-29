package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.print.attribute.standard.MediaSize.NA;

@Entity
@Table(name = "ORDERS")
public class Order {

	@Id @GeneratedValue
	@Column(name = "ORDER_ID")
	private Long id;
	@Column(name = "MEMBER_ID")
	private Long memberId;
	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public Long getId() {
		return id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
