import React from "react";

interface MenuHeaderProps {
  onClose: () => void;
}

export const MenuHeader: React.FC<MenuHeaderProps> = ({ onClose }) => {
  return (
    <div className="flex items-center justify-between p-4 border-b border-gray-200 bg-linear-to-r from-blue-600 to-blue-700">
      <div className="flex items-center space-x-3">
        <div className="w-8 h-8 bg-white text-blue-600 rounded-full flex items-center justify-center">
          <svg className="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
            <path
              fillRule="evenodd"
              d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
              clipRule="evenodd"
            />
          </svg>
        </div>
        <div className="flex flex-col">
          <span className="text-white font-medium text-xs">Usuário Admin</span>
          <span className="text-blue-200 text-xs">admin@opticlinic.com</span>
        </div>
      </div>

      <div className="flex items-center space-x-2">
        <button
          className="text-white hover:bg-white hover:bg-opacity-20 rounded-full p-1 transition-colors duration-200"
          title="Sair do sistema"
        >
          <svg
            className="w-5 h-5"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
            />
          </svg>
        </button>

        <button
          onClick={onClose}
          className="lg:hidden text-white hover:bg-white hover:bg-opacity-20 rounded-full p-1 transition-colors duration-200"
          aria-label="Fechar menu"
        >
          <svg
            className="w-6 h-6"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M6 18L18 6M6 6l12 12"
            />
          </svg>
        </button>
      </div>
    </div>
  );
};
