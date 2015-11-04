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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Produto implements Serializable
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

   @Column
   private String nome;

   @Column
   private String descricao;

   @Column
   private BigDecimal valor;

   @Column(name = "imagem")
   private String imagem;

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
      if (!(obj instanceof Produto))
      {
         return false;
      }
      Produto other = (Produto) obj;
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

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public String getDescricao()
   {
      return descricao;
   }

   public void setDescricao(String descricao)
   {
      this.descricao = descricao;
   }

   public BigDecimal getValor()
   {
      return valor;
   }

   public void setValor(BigDecimal valor)
   {
      this.valor = valor;
   }

   public String getImagem()
   {
      return imagem;
   }

   public void setImagem(String imagem)
   {
      this.imagem = imagem;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (nome != null && !nome.trim().isEmpty())
         result += "nome: " + nome;
      if (descricao != null && !descricao.trim().isEmpty())
         result += ", descricao: " + descricao;
      if (imagem != null && !imagem.trim().isEmpty())
         result += ", imagem: " + imagem;
      return result;
   }
}