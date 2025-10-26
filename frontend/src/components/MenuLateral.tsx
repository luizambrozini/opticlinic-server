import { MenuHeader } from "./menu/MenuHeader";
import { MenuSection } from "./menu/MenuSection";
import { menuStructure } from "./menu/menuData";
import type { MenuSection as MenuSectionType } from "./menu/types";

interface MenuLateralProps {
  isOpen: boolean;
  onClose: () => void;
}

const MenuLateral: React.FC<MenuLateralProps> = ({ isOpen, onClose }) => {
  return (
    <>
      {/* Overlay para fechar o menu ao clicar fora */}
      {isOpen && (
        <div
          className="fixed inset-0 bg-black bg-opacity-50 z-40 lg:hidden"
          onClick={onClose}
        />
      )}

      {/* Menu lateral */}
      <aside
        className={`
        fixed top-0 left-0 h-screen w-72 bg-white shadow-2xl z-50 transform transition-transform duration-300 ease-in-out border-r border-gray-200
        ${isOpen ? "translate-x-0" : "-translate-x-full"}
        lg:translate-x-0
      `}
      >
        <div className="flex flex-col h-full overflow-hidden">
          <MenuHeader onClose={onClose} />

          <nav className="flex-1 overflow-y-auto py-4 px-3">
            <div className="space-y-1">
              {menuStructure.map((section: MenuSectionType) => (
                <MenuSection key={section.id} section={section} />
              ))}
            </div>
          </nav>
        </div>
      </aside>
    </>
  );
};

export default MenuLateral;
