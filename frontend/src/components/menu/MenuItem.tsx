import React, { useState } from "react";
import { useWorkspace } from "../../contexts/WorkspaceContext";
import { routeComponents } from "../../utils/routeMapping";
import type { MenuItem as MenuItemType } from "./types";

interface MenuItemProps {
  item: MenuItemType;
  depth?: number;
}

export const MenuItem: React.FC<MenuItemProps> = ({ item, depth = 0 }) => {
  const [isExpanded, setIsExpanded] = useState(false);
  const { openWindow } = useWorkspace();
  const hasSubItems = item.items && item.items.length > 0;

  const paddingLeft = depth * 16 + 16; // 16px base + 16px per depth level

  const handleClick = () => {
    if (hasSubItems) {
      setIsExpanded(!isExpanded);
    } else if (item.path) {
      // Abrir janela no workspace
      const routeConfig = routeComponents[item.path];
      if (routeConfig) {
        openWindow({
          title: routeConfig.title,
          path: item.path,
          component: React.createElement(routeConfig.component),
        });
      }
    }
  };

  return (
    <div>
      <button
        onClick={handleClick}
        className={`
          w-full flex items-center justify-between px-3 py-2 text-left text-xs rounded-lg transition-all duration-200
          ${
            depth === 0
              ? "text-gray-700 hover:bg-blue-50 hover:text-blue-700"
              : "text-gray-600 hover:bg-gray-100 hover:text-gray-800"
          }
          ${
            hasSubItems
              ? "cursor-pointer font-bold"
              : "cursor-pointer hover:pl-4 font-medium"
          }
        `}
        style={{ paddingLeft: `${paddingLeft}px` }}
      >
        <div className="flex items-center">
          <span className="truncate">
            {hasSubItems ? `> ${item.label}` : `• ${item.label}`}
          </span>
        </div>

        {hasSubItems && (
          <svg
            className={`w-4 h-4 transition-transform duration-200 ${
              isExpanded ? "rotate-90" : ""
            }`}
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
        )}
      </button>

      {hasSubItems && isExpanded && (
        <div className="mt-0.5 ml-4 bg-gray-50 rounded-md py-1 space-y-0.5">
          {item.items!.map((subItem) => (
            <MenuItem key={subItem.id} item={subItem} depth={depth + 1} />
          ))}
        </div>
      )}
    </div>
  );
};
