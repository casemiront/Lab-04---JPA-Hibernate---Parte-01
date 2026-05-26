package br.ifma.labbd.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "clientes")
public class Cliente implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome_cliente", length = 255, nullable = false)
    private String nomeCliente;

    @Column(name = "cpf", length = 14, unique = true, nullable = false)
    private String cpf;

    @Column(name = "telefone1", length = 11)
    private String telefone1;

    @Column(name = "telefone2", length = 11)
    private String telefone2;

    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_nascimento")
    private Date dtNascimento;

    public Cliente() {}

    public Cliente(String nomeCliente, String cpf, String email) {
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.email = email;
    }

    @Override
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone1() { return telefone1; }
    public void setTelefone1(String telefone1) { this.telefone1 = telefone1; }

    public String getTelefone2() { return telefone2; }
    public void setTelefone2(String telefone2) { this.telefone2 = telefone2; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(Date dtNascimento) { this.dtNascimento = dtNascimento; }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nome='" + nomeCliente + "', cpf='" + cpf + "'}";
    }
}
