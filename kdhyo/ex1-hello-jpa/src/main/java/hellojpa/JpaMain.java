package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		try {
			Member member = new Member();
			member.setUsername("member1");
			member.setHomeAddress(new Address("homeCity", "street", "zipcode"));

			member.getFavoriteFoods().add("치킨");
			member.getFavoriteFoods().add("족발");
			member.getFavoriteFoods().add("피자");

			member.getAddressEntities().add(new AddressEntity("old1", "street", "10000"));
			member.getAddressEntities().add(new AddressEntity("old2", "street", "10000"));

			em.persist(member);

			em.flush();
			em.clear();

			System.out.println("================ START ================");
			Member findMember = em.find(Member.class, member.getId());

			// homeCity -> NewCity
			Address a = findMember.getHomeAddress();
			findMember.setHomeAddress(new Address("NewCity", a.getStreet(), a.getZipcode()));

			// 치킨 -> 한식
			findMember.getFavoriteFoods().remove("치킨");
			findMember.getFavoriteFoods().add("한식");

//			findMember.getAddressEntities().remove(new AddressEntity("old1", "street", "10000"));
//			findMember.getAddressEntities().add(new AddressEntity("newCity1", "street", "10000"));

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		emf.close();
	}

}
