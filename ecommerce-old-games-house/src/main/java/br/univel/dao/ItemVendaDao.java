package br.univel.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import br.univel.model.ItemVenda;

/**
 *  DAO for ItemVenda
 */
@Stateless
public class ItemVendaDao
{
   @PersistenceContext(unitName = "ecommerce-old-games-house-persistence-unit")
   private EntityManager em;

   public void create(ItemVenda entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      ItemVenda entity = em.find(ItemVenda.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public ItemVenda findById(Long id)
   {
      return em.find(ItemVenda.class, id);
   }

   public ItemVenda update(ItemVenda entity)
   {
      return em.merge(entity);
   }

   public List<ItemVenda> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<ItemVenda> findAllQuery = em.createQuery("SELECT DISTINCT i FROM ItemVenda i ORDER BY i.id", ItemVenda.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      return findAllQuery.getResultList();
   }
}
