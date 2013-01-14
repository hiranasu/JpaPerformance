package org.hiranasu.jpaperformance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * JPAクエリ表現ごとのパフォーマンスを検証
 * 
 * @author hiranasu
 *
 */
public class JpaPerformanceTester {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-app");
		EntityManager em = emf.createEntityManager();
		try {
			// 10万件のテストデータ投入
			em.getTransaction().begin();
			for (int i = 0; i < 100000; i++) {
				Product p = new Product();
				p.setId(BigDecimal.valueOf(i));
				p.setName("sampledata" + i);
				em.persist(p);
			}
			em.getTransaction().commit();
			
			// ランダムな1件をselectする
			Random r = new Random(1);
			
			// Entityでselect
			long start = System.currentTimeMillis();
//			for (int i = 0; i < 1000; i++) {
//				Product product = em.find(Product.class, BigDecimal.valueOf(r.nextInt(100000)));
//			}
			long finish = System.currentTimeMillis();
//			System.out.println(finish - start);
			
			// NativeQuery
			start = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				int lowerIndex = r.nextInt(100000);
				int upperIndex = lowerIndex + 1000;
				Query query = em.createNamedQuery("findProductsByNative");
				query.setParameter("lowerId", lowerIndex);
				query.setParameter("upperId", upperIndex);
				List<Product> products = (List<Product>)query.getResultList();
			}
			finish = System.currentTimeMillis();
			System.out.println(finish - start);

			// JPQL
			start = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
//				List<Product> productsJPQL = em.createQuery("select p from Product p where p.id = " + r.nextInt(100000)).getResultList();

				// try NamedQuery
				int lowerIndex = r.nextInt(100000);
				int upperIndex = lowerIndex + 1000;
				Query query = em.createNamedQuery("findProducts");
				query.setParameter("lowerId", lowerIndex);
				query.setParameter("upperId", upperIndex);
				List<Product> productsJPQL = (List<Product>)query.getResultList();
			}
			finish = System.currentTimeMillis();
			System.out.println(finish - start);
			
			// criteria API
//			start = System.currentTimeMillis();
//			for (int i = 0; i < 1000; i++) {
//				CriteriaBuilder cb = em.getCriteriaBuilder();
//				CriteriaQuery<Product> cq = cb.createQuery(Product.class);
//				Root<Product> rt = cq.from(Product.class);
//				cq.select(rt).where(cb.equal(rt.get("id"), r.nextInt(100000)));
//				List<Product> productsCriteria = em.createQuery(cq).getResultList();
//			}
//			finish = System.currentTimeMillis();
//			System.out.println(finish - start);
			
			
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
		
	}
}
