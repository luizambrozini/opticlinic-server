import { useState } from "react";
import "./App.css";
import { useUserSession } from "../hooks/get-user-session";
import MenuLateral from "./components/MenuLateral";
import Workspace from "./components/Workspace";
import { WorkspaceProvider } from "./contexts/WorkspaceContext";

export function AppRestricted() {
  const { loading } = useUserSession();
  const [menuOpen, setMenuOpen] = useState(false);

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p className="text-gray-600">Carregando sessão...</p>
        </div>
      </div>
    );
  }

  //   if (!session) {
  //     return <Navigate to="/login" replace />;
  //   }

  return (
    <WorkspaceProvider>
      <div className="h-screen bg-gray-50 flex overflow-hidden">
        {/* Menu Lateral */}
        <MenuLateral isOpen={menuOpen} onClose={() => setMenuOpen(false)} />

        {/* Área Principal */}
        <div className="flex-1 flex flex-col overflow-hidden lg:ml-72">
          {/* Header da aplicação */}
          <header className="bg-white shadow-sm border-b border-gray-200 px-6 py-4">
            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-4">
                <button
                  onClick={() => setMenuOpen(!menuOpen)}
                  className="lg:hidden text-gray-600 hover:text-gray-900 focus:outline-none"
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
                      d="M4 6h16M4 12h16M4 18h16"
                    />
                  </svg>
                </button>
                <h1 className="text-xl font-semibold text-gray-900">
                  Opticlinic
                </h1>
              </div>

              <div className="flex items-center space-x-4">
                <button className="text-gray-600 hover:text-gray-900 relative">
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
                      d="M15 17h5l-5 5v-5zM12 10.5A4.5 4.5 0 108 6a4.5 4.5 0 004 4.5z"
                    />
                  </svg>
                  <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">
                    3
                  </span>
                </button>

                <div className="flex items-center space-x-2">
                  <div className="w-8 h-8 bg-blue-600 text-white rounded-full flex items-center justify-center text-sm font-medium">
                    A
                  </div>
                  <span className="text-sm font-medium text-gray-700">
                    Admin
                  </span>
                </div>
              </div>
            </div>
          </header>

          {/* Área de Trabalho */}
          <div className="flex-1 min-h-0 overflow-hidden">
            <Workspace />
          </div>
        </div>
      </div>
    </WorkspaceProvider>
  );
}

export default AppRestricted;
