import React, { useState } from "react";
import { MenuItem } from "./MenuItem";
import type { MenuSection as MenuSectionType } from "./types";

interface MenuSectionProps {
  section: MenuSectionType;
}

export const MenuSection: React.FC<MenuSectionProps> = ({ section }) => {
  const [isExpanded, setIsExpanded] = useState(false);

  return (
    <div className="mb-1">
      <button
        onClick={() => setIsExpanded(!isExpanded)}
        className="w-full flex items-center justify-between px-3 py-2 text-left font-semibold text-gray-800 hover:bg-gray-50 rounded-lg transition-colors duration-200"
      >
        <div className="flex items-center">
          <span className="text-xs tracking-wide">{section.title}</span>
        </div>

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
      </button>

      {isExpanded && (
        <div className="mt-1 ml-2 bg-gray-50 bg-opacity-50 rounded-lg p-1 space-y-0.5">
          {section.items.map((item) => (
            <MenuItem key={item.id} item={item} />
          ))}
        </div>
      )}
    </div>
  );
};
