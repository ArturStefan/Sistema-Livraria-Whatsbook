-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 20-Nov-2018 às 14:23
-- Versão do servidor: 10.1.35-MariaDB
-- versão do PHP: 7.2.9

SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `biblioteca`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cadastro_clientes`
--

CREATE TABLE `cadastro_clientes` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `cpf` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `rg` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `idade` int(11) NOT NULL,
  `nascimento` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `endereco` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `numero` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `bairro` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `cidade` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `telefone` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `celular` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `data` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `cep` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `usuario` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `senha` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `funcao` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `cadastro_funcionários`
--

CREATE TABLE `cadastro_funcionários` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `funcao` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `cpf` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `rg` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `endereco` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `numero` int(10) NOT NULL,
  `bairro` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `cidade` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `telefone` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `celular` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `data` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `cep` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `usuario` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `senha` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `salario` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `cadastro_livros`
--

CREATE TABLE `cadastro_livros` (
  `tombo` int(11) NOT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `autor` varchar(100) DEFAULT NULL,
  `genero` varchar(50) DEFAULT NULL,
  `conservacao` varchar(10) DEFAULT NULL,
  `lancamento` varchar(20) DEFAULT NULL,
  `idioma` varchar(20) DEFAULT NULL,
  `editora` varchar(50) DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `data` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `preco_compra` double NOT NULL,
  `preco_venda` double NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `cadastro_livros_fotos`
--

CREATE TABLE `cadastro_livros_fotos` (
  `cod` int(11) NOT NULL,
  `foto` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `financeiro`
--

CREATE TABLE `financeiro` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `saldo_anterior` double NOT NULL,
  `pag_salario` double NOT NULL,
  `comp_livros` double NOT NULL,
  `livros_vendidos` int(11) NOT NULL,
  `lucro_prejuizo` double NOT NULL,
  `data_mes` varchar(12) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `notafiscal`
--

CREATE TABLE `notafiscal` (
  `idnota` int(11) UNSIGNED NOT NULL,
  `idvenda_clt` int(11) DEFAULT NULL,
  `datavenda` varchar(30) DEFAULT NULL,
  `cpf_cliente` varchar(25) DEFAULT NULL,
  `nome_cliente` varchar(100) DEFAULT NULL,
  `valor_total_nota` double DEFAULT NULL,
  `nome_vendedor` varchar(100) DEFAULT NULL,
  `cod_vendedor` int(11) DEFAULT NULL,
  `cpf_vendedor` varchar(25) DEFAULT NULL,
  `credito` varchar(10) NOT NULL,
  `debito` varchar(10) NOT NULL,
  `dinheiro` varchar(10) NOT NULL,
  `valor_entrada` double NOT NULL,
  `valor_troco` double NOT NULL,
  `valor_parcelas` double NOT NULL,
  `num_parcelas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `sugestao`
--

CREATE TABLE `sugestao` (
  `id` int(11) NOT NULL,
  `mensagem` varchar(220) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vendas`
--

CREATE TABLE `vendas` (
  `idvenda` int(11) NOT NULL,
  `datavenda` varchar(20) DEFAULT NULL,
  `idcliente` int(11) DEFAULT NULL,
  `cpf` varchar(30) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `tombolivro` int(11) DEFAULT NULL,
  `nome_livro` varchar(50) NOT NULL,
  `preco_unit` double NOT NULL,
  `unid_compra` int(20) NOT NULL,
  `total_item` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cadastro_clientes`
--
ALTER TABLE `cadastro_clientes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cpf` (`cpf`);

--
-- Indexes for table `cadastro_funcionários`
--
ALTER TABLE `cadastro_funcionários`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cpf` (`cpf`);

--
-- Indexes for table `cadastro_livros`
--
ALTER TABLE `cadastro_livros`
  ADD PRIMARY KEY (`tombo`),
  ADD KEY `id` (`id`);

--
-- Indexes for table `financeiro`
--
ALTER TABLE `financeiro`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `notafiscal`
--
ALTER TABLE `notafiscal`
  ADD PRIMARY KEY (`idnota`),
  ADD UNIQUE KEY `idnota` (`idnota`),
  ADD KEY `idvenda_clt` (`idvenda_clt`),
  ADD KEY `cod_vendedor` (`cod_vendedor`);

--
-- Indexes for table `sugestao`
--
ALTER TABLE `sugestao`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendas`
--
ALTER TABLE `vendas`
  ADD PRIMARY KEY (`idvenda`),
  ADD KEY `idcliente` (`idcliente`),
  ADD KEY `tombolivro` (`tombolivro`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cadastro_clientes`
--
ALTER TABLE `cadastro_clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cadastro_funcionários`
--
ALTER TABLE `cadastro_funcionários`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cadastro_livros`
--
ALTER TABLE `cadastro_livros`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `financeiro`
--
ALTER TABLE `financeiro`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notafiscal`
--
ALTER TABLE `notafiscal`
  MODIFY `idnota` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sugestao`
--
ALTER TABLE `sugestao`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendas`
--
ALTER TABLE `vendas`
  MODIFY `idvenda` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `notafiscal`
--
ALTER TABLE `notafiscal`
  ADD CONSTRAINT `notafiscal_ibfk_1` FOREIGN KEY (`idvenda_clt`) REFERENCES `vendas` (`idvenda`),
  ADD CONSTRAINT `notafiscal_ibfk_2` FOREIGN KEY (`cod_vendedor`) REFERENCES `cadastro_funcionários` (`id`);

--
-- Limitadores para a tabela `vendas`
--
ALTER TABLE `vendas`
  ADD CONSTRAINT `vendas_ibfk_1` FOREIGN KEY (`idcliente`) REFERENCES `cadastro_clientes` (`id`),
  ADD CONSTRAINT `vendas_ibfk_2` FOREIGN KEY (`tombolivro`) REFERENCES `cadastro_livros` (`tombo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
