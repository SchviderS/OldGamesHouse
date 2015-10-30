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
import br.univel.model.Cliente;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column(name = "login")
   private String login;

   @Column(name = "senha")
   private String senha;

   @Column(name = "admin")
   private Boolean admin;

   @OneToOne(fetch = FetchType.LAZY)
   private Cliente Cliente;

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
      if (!(obj instanceof Usuario))
      {
         return false;
      }
      Usuario other = (Usuario) obj;
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

   public String getLogin()
   {
      return login;
   }

   public void setLogin(String login)
   {
      this.login = login;
   }

   public String getSenha()
   {
      return senha;
   }

   public void setSenha(String senha)
   {
      this.senha = senha;
   }

   public Boolean getAdmin()
   {
      return admin;
   }

   public void setAdmin(Boolean admin)
   {
      this.admin = admin;
   }

   public Cliente getCliente()
   {
      return Cliente;
   }

   public void setCliente(Cliente Cliente)
   {
      this.Cliente = Cliente;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (login != null && !login.trim().isEmpty())
         result += "login: " + login;
      if (senha != null && !senha.trim().isEmpty())
         result += ", senha: " + senha;
      if (admin != null)
         result += ", admin: " + admin;
      return result;
   }
}