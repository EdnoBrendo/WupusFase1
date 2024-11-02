# 🏫 UNIFACISA - Centro Universitário Unifacisa

## Projeto: Sistema de Navegação do Robô

![Unifacisa](https://www.vestibularesmedicina.com.br/storage/photos/unifacisa-1605220436.jpg)

---

### 📋 Alunos:
- **Edno Silva**
- **Caio Lorran**
- **Kevin Tenório**

---

## 📌 Descrição do Projeto

Este projeto é uma simulação de um sistema de navegação para um robô em um mapa de pontos. A lógica de movimento é baseada em **estratégias configuráveis**, permitindo ao robô decidir o próximo ponto de destino com base em diferentes critérios. A estratégia implementada atualmente utiliza a menor distância entre o robô e o próximo ponto para escolher o caminho.

---

## 🚀 Como Funciona

1. **Estratégia de Movimento**: Através da interface `Strategy`, o sistema permite definir diferentes formas de decidir o próximo movimento do robô.
2. **Movimento com Menor Distância**: Na implementação `ShortestDistance`, o próximo passo é determinado pelo ponto mais próximo da posição atual do robô, usando a distância euclidiana.
3. **Execução**: A classe `Execute` inicia a simulação, configurando o robô para usar a estratégia `ShortestDistance`.

---

## 🔧 Requisitos

- **Java 8** ou superior

---

## 📂 Estrutura do Projeto

- **Execute.java**: Ponto de entrada do programa, responsável por iniciar a simulação.
- **Strategy.java**: Interface para diferentes estratégias de movimentação.
- **ShortestDistance.java**: Implementação da estratégia de menor distância, utilizando a distância euclidiana.

---

