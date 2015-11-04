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
import br.univel.model.Usuario;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "cliente")
@XmlRootElement
public class Cliente implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   /**
    * 
    */
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column(name = "nome")
   private String nome;

   @Column(length = 15, name = "telefone")
   private String Telefone;

   @Column(name = "email")
   private String email;

   @OneToOne(fetch = FetchType.LAZY)
   private Usuario Usuario;

   @Column(name = "endereco")
   private String endereco;

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
      if (!(obj instanceof Cliente))
      {
         return false;
      }
      Cliente other = (Cliente) obj;
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

   public String getTelefone()
   {
      return Telefone;
   }

   public void setTelefone(String Telefone)
   {
      this.Telefone = Telefone;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public Usuario getUsuario()
   {
      return Usuario;
   }

   public void setUsuario(Usuario Usuario)
   {
      this.Usuario = Usuario;
   }

   public String getEndereco()
   {
      return endereco;
   }

   public void setEndereco(String endereco)
   {
      this.endereco = endereco;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (nome != null && !nome.trim().isEmpty())
         result += "nome: " + nome;
      if (Telefone != null && !Telefone.trim().isEmpty())
         result += ", Telefone: " + Telefone;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (endereco != null && !endereco.trim().isEmpty())
         result += ", endereco: " + endereco;
      return result;
   }
}