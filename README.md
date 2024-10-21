# Requisições GET e POST
Vendedor GET: http://localhost:8080/vendedor
Vendedor POST: http://localhost:8080/vendedor
# Body JSON: 
{
   "nome": "Adilson",
   "cpf": "12345678800"
}

Produto GET: http://localhost:8080/produto
Produto POST: http://localhost:8080/produto
# Body JSON: 
{
    "nome": "Mouse",
    "valor": 40.29
}

Venda GET Periodo de data média: http://localhost:8080/venda/media?dataInicio=10/10/2024&dataFim=01/11/2024
Venda GET lista de vendas: http://localhost:8080/venda
Venda POST: http://localhost:8080/venda
# Body JSON: 
{
  "produtoId": 1,
  "vendedorId": 1,
  "quantidade": 1
}
