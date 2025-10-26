import React, { useState, useMemo } from "react";
import Button from "./Button";
import ButtonIcon from "./ButtonIcon";

interface Column {
  key: string;
  label: string;
  width?: string;
}

interface DataGridProps {
  data: any[];
  columns: Column[];
  total: number;
  currentPage: number;
  limit: number;
  onPageChange: (page: number) => void;
  onSelectionChange?: (selectedItem: any, selectedIndex: number | null) => void;
  className?: string;
}

const DataGrid: React.FC<DataGridProps> = ({
  data,
  columns,
  total,
  currentPage,
  limit,
  onPageChange,
  onSelectionChange,
  className = "",
}) => {
  const [searchField, setSearchField] = useState(columns[0]?.key || "");
  const [searchValue, setSearchValue] = useState("");
  const [selectedIndex, setSelectedIndex] = useState<number | null>(null);

  // Filtrar dados baseado na pesquisa (apenas local, paginação é externa)
  const filteredData = useMemo(() => {
    if (!searchValue.trim()) return data;

    return data.filter((item) => {
      const fieldValue = item[searchField];
      if (fieldValue === null || fieldValue === undefined) return false;

      return fieldValue
        .toString()
        .toLowerCase()
        .includes(searchValue.toLowerCase());
    });
  }, [data, searchField, searchValue]);

  // Calcular informações de paginação
  const totalPages = Math.ceil(total / limit);
  const startIndex = (currentPage - 1) * limit + 1;
  const currentData = filteredData; // Dados já vêm paginados externamente

  // Funções de navegação
  const goToFirstPage = () => onPageChange(1);
  const goToPreviousPage = () => onPageChange(Math.max(1, currentPage - 1));
  const goToNextPage = () =>
    onPageChange(Math.min(totalPages, currentPage + 1));
  const goToLastPage = () => onPageChange(totalPages);
  const refreshData = () => {
    onPageChange(1);
    setSearchValue("");
    setSelectedIndex(null);
  };

  // Função para selecionar item
  const handleItemSelect = (item: any, index: number) => {
    const globalIndex = (currentPage - 1) * limit + index;
    setSelectedIndex(globalIndex);
    onSelectionChange?.(item, globalIndex);
  };

  // Reset seleção quando pesquisa muda
  React.useEffect(() => {
    onPageChange(1);
    setSelectedIndex(null);
  }, [searchValue, searchField, onPageChange]);
  return (
    <div
      className={`bg-white rounded-lg shadow flex flex-col h-full ${className}`}
    >
      {/* Barra de pesquisa e navegação */}
      <div className="p-4 border-b border-gray-200 shrink-0">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-4">
            <div className="flex items-center space-x-2">
              <label className="text-[10px] font-medium text-gray-700">
                Pesquisar por:
              </label>
              <select
                value={searchField}
                onChange={(e) => setSearchField(e.target.value)}
                className="border border-gray-300 rounded-md px-3 py-1.5 text-[10px] focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                {columns.map((column) => (
                  <option key={column.key} value={column.key}>
                    {column.label}
                  </option>
                ))}
              </select>
            </div>
            <div className="flex-1 max-w-md">
              <input
                type="text"
                placeholder="Digite o valor para pesquisar..."
                value={searchValue}
                onChange={(e) => setSearchValue(e.target.value)}
                className="w-full border border-gray-300 rounded-md px-3 py-1.5 text-[10px] focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <Button variant="secondary" size="sm" onClick={refreshData}>
              Limpar
            </Button>
          </div>

          {/* Botões de navegação e informações */}
          <div className="flex items-center space-x-4">
            {/* Botões de navegação */}
            <div className="flex items-center space-x-1">
              <ButtonIcon
                variant="secondary"
                size="sm"
                onClick={goToFirstPage}
                disabled={currentPage === 1}
                title="Primeira página"
              >
                <svg
                  className="w-3 h-3"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M11 19l-7-7 7-7m8 14l-7-7 7-7"
                  />
                </svg>
              </ButtonIcon>

              <ButtonIcon
                variant="secondary"
                size="sm"
                onClick={goToPreviousPage}
                disabled={currentPage === 1}
                title="Página anterior"
              >
                <svg
                  className="w-3 h-3"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M15 19l-7-7 7-7"
                  />
                </svg>
              </ButtonIcon>

              <ButtonIcon
                variant="secondary"
                size="sm"
                onClick={refreshData}
                title="Atualizar"
              >
                <svg
                  className="w-3 h-3"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"
                  />
                </svg>
              </ButtonIcon>

              <ButtonIcon
                variant="secondary"
                size="sm"
                onClick={goToNextPage}
                disabled={currentPage === totalPages || totalPages === 0}
                title="Próxima página"
              >
                <svg
                  className="w-3 h-3"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M9 5l7 7-7 7"
                  />
                </svg>
              </ButtonIcon>

              <ButtonIcon
                variant="secondary"
                size="sm"
                onClick={goToLastPage}
                disabled={currentPage === totalPages || totalPages === 0}
                title="Última página"
              >
                <svg
                  className="w-3 h-3"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M13 5l7 7-7 7M5 5l7 7-7 7"
                  />
                </svg>
              </ButtonIcon>
            </div>

            {/* Informações de página */}
            <div className="text-[10px] text-gray-600">
              Página {totalPages > 0 ? currentPage : 0} de {totalPages} Total:{" "}
              {total} registro(s)
            </div>
          </div>
        </div>
      </div>

      {/* Grid de dados */}
      <div className="flex-1 overflow-hidden flex flex-col">
        <div className="overflow-x-auto overflow-y-auto flex-1 border border-gray-200 rounded-lg">
          <div
            className="min-w-full h-full"
            style={{ minWidth: `${columns.length * 200}px` }}
          >
            {/* Cabeçalho */}
            <div className="bg-gray-50 border-b border-gray-200 sticky top-0 z-10">
              <div
                className="grid gap-2 px-4 py-2"
                style={{
                  gridTemplateColumns: columns
                    .map((col) => col.width || "200px")
                    .join(" "),
                  minWidth: `${columns.length * 200}px`,
                }}
              >
                {columns.map((column) => (
                  <div
                    key={column.key}
                    className="text-left font-medium text-gray-500 uppercase tracking-wider text-[10px]"
                  >
                    {column.label}
                  </div>
                ))}
              </div>
            </div>

            {/* Dados */}
            <div className="bg-white divide-y divide-gray-200 flex-1 min-h-0">
              {currentData.length > 0 ? (
                currentData.map((item, index) => {
                  const globalIndex = startIndex + index;
                  const isSelected = selectedIndex === globalIndex;

                  return (
                    <div
                      key={globalIndex}
                      className={`grid gap-2 px-4 py-2 transition-colors cursor-pointer ${
                        isSelected
                          ? "bg-blue-50 border-l-4 border-blue-500"
                          : "hover:bg-gray-50"
                      }`}
                      style={{
                        gridTemplateColumns: columns
                          .map((col) => col.width || "200px")
                          .join(" "),
                        minWidth: `${columns.length * 200}px`,
                      }}
                      onClick={() => handleItemSelect(item, index)}
                    >
                      {columns.map((column) => (
                        <div
                          key={column.key}
                          className={`text-[10px] ${
                            isSelected
                              ? "text-blue-900 font-medium"
                              : "text-gray-900"
                          }`}
                          style={{
                            width: column.width || "200px",
                            minWidth: column.width || "200px",
                            wordWrap: "break-word",
                            overflowWrap: "break-word",
                          }}
                          title={item[column.key]}
                        >
                          {item[column.key] || "-"}
                        </div>
                      ))}
                    </div>
                  );
                })
              ) : (
                <div className="flex-1 flex items-center justify-center text-center text-gray-500">
                  <div className="text-[10px]">
                    {searchValue
                      ? "Nenhum resultado encontrado para a pesquisa."
                      : "Nenhum dado disponível."}
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DataGrid;
