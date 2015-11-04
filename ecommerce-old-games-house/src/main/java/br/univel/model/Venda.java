package br.univel.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;
import java.math.BigDecimal;
import java.sql.Date;
import br.univel.model.Produto;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import br.univel.model.Cliente;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Venda implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column(name = "total")
   private BigDecimal total;

   @Column(name = "data")
   private Date data;

   @OneToMany
   private Set<Produto> produtos = new HashSet<Produto>();

   @ManyToOne(fetch = FetchType.LAZY)
   private Cliente cliente;

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
      if (!(obj instanceof Venda))
      {
         return false;
      }
      Venda other = (Venda) obj;
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

   public BigDecimal getTotal()
   {
      return total;
   }

   public void setTotal(BigDecimal total)
   {
      this.total = total;
   }

   public Date getData()
   {
      return data;
   }

   public void setData(Date data)
   {
      this.data = data;
   }

   public Set<Produto> getProdutos()
   {
      return this.produtos;
   }

   public void setProdutos(final Set<Produto> produtos)
   {
      this.produtos = produtos;
   }

   public Cliente getCliente()
   {
      return this.cliente;
   }

   public void setCliente(final Cliente cliente)
   {
      this.cliente = cliente;
   }

}