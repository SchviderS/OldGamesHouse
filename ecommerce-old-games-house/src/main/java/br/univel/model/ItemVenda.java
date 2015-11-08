package br.univel.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import java.math.BigDecimal;

@Entity
@Table(name = "ItemVenda")
public class ItemVenda implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.TABLE)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column
   private int idProduto;

   @Column
   private int quantidade;

   @Column
   private BigDecimal valor;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof ItemVenda))
      {
         return false;
      }
      ItemVenda other = (ItemVenda) obj;
      if (id != null)
      {
         if (!id.equals(other.id))
         {
            return false;
         }
      }
      return true;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   public int getIdProduto()
   {
      return idProduto;
   }

   public void setIdProduto(int idProduto)
   {
      this.idProduto = idProduto;
   }

   public int getQuantidade()
   {
      return quantidade;
   }

   public void setQuantidade(int quantidade)
   {
      this.quantidade = quantidade;
   }

   public BigDecimal getValor()
   {
      return valor;
   }

   public void setValor(BigDecimal valor)
   {
      this.valor = valor;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "idProduto: " + idProduto;
      result += ", quantidade: " + quantidade;
      return result;
   }
}