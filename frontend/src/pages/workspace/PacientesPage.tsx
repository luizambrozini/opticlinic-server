import React, { useState, useEffect, useMemo } from "react";
import Button from "../../components/Button";
import DataGrid from "../../components/DataGrid";

const PacientesPage: React.FC = () => {
  const [selectedPaciente, setSelectedPaciente] = useState<any>(null);
  const [selectedIndex, setSelectedIndex] = useState<number | null>(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [limit] = useState(10);
  const [total, setTotal] = useState(0);

  // Dados de exemplo expandidos - usando useMemo para evitar recriação
  const allPacientesData = useMemo(
    () =>
      Array.from({ length: 100 }, (_, i) => ({
        nome: `Paciente ${i + 1}`,
        cpf: `${String(i + 100).padStart(3, "0")}.${String(i + 200).padStart(
          3,
          "0"
        )}.${String(i + 300).padStart(3, "0")}-${String(i % 100).padStart(
          2,
          "0"
        )}`,
        telefone: `(11) ${String(90000 + i).padStart(5, "0")}-${String(
          1000 + i
        ).padStart(4, "0")}`,
        consulta: `${String((i % 30) + 1).padStart(2, "0")}/10/2024`,
      })),
    []
  );

  // Simular dados paginados
  const [pacientesData, setPacientesData] = useState<any[]>([]);

  // Simular chamada à API para buscar dados paginados
  useEffect(() => {
    const startIndex = (currentPage - 1) * limit;
    const endIndex = startIndex + limit;
    const paginatedData = allPacientesData.slice(startIndex, endIndex);

    setPacientesData(paginatedData);
    setTotal(allPacientesData.length);
  }, [currentPage, limit]);

  // Handler para mudança de página
  const handlePageChange = (page: number) => {
    setCurrentPage(page);
    setSelectedPaciente(null);
    setSelectedIndex(null);
  };

  // Definição das colunas
  const columns = [
    { key: "nome", label: "Nome", width: "300px" },
    { key: "cpf", label: "CPF", width: "150px" },
    { key: "telefone", label: "Telefone", width: "180px" },
    { key: "consulta", label: "Última Consulta", width: "100px" },
  ];

  // Handlers para ações
  const handleSelectionChange = (
    selectedItem: any,
    selectedIndex: number | null
  ) => {
    setSelectedPaciente(selectedItem);
    setSelectedIndex(selectedIndex);
    console.log(
      "Paciente selecionado:",
      selectedItem,
      "Índice:",
      selectedIndex
    );
  };

  const handleEdit = () => {
    if (selectedPaciente) {
      console.log(
        "Editar paciente:",
        selectedPaciente,
        "Índice:",
        selectedIndex
      );
      // Aqui você implementaria a lógica de edição
    }
  };

  const handleDelete = () => {
    if (selectedPaciente) {
      console.log(
        "Excluir paciente:",
        selectedPaciente,
        "Índice:",
        selectedIndex
      );
      // Aqui você implementaria a lógica de exclusão
    }
  };

  const handleNew = () => {
    console.log("Novo paciente");
    // Aqui você implementaria a lógica para criar novo paciente
  };

  return (
    <div className="p-2 h-full flex flex-col">
      <div className="mb-2 shrink-0">
        <div className="flex justify-between items-center">
          <div className="flex space-x-2">
            <Button variant="primary" size="md" onClick={handleNew}>
              Novo
            </Button>
            <Button
              variant="secondary"
              size="md"
              onClick={handleEdit}
              disabled={!selectedPaciente}
            >
              Editar
            </Button>
            <Button
              variant="danger"
              size="md"
              onClick={handleDelete}
              disabled={!selectedPaciente}
            >
              Excluir
            </Button>
          </div>
        </div>
      </div>

      <DataGrid
        data={pacientesData}
        columns={columns}
        total={total}
        currentPage={currentPage}
        limit={limit}
        onPageChange={handlePageChange}
        onSelectionChange={handleSelectionChange}
        className="flex-1 min-h-0"
      />
    </div>
  );
};

export default PacientesPage;
