import React, { createContext, useContext, useState } from "react";
import type { ReactNode } from "react";

export interface WorkspaceWindow {
  id: string;
  title: string;
  path: string;
  component: ReactNode;
  isActive: boolean;
}

interface WorkspaceContextType {
  windows: WorkspaceWindow[];
  activeWindowId: string | null;
  openWindow: (window: Omit<WorkspaceWindow, "id" | "isActive">) => void;
  closeWindow: (id: string) => void;
  setActiveWindow: (id: string) => void;
  closeAllWindows: () => void;
}

const WorkspaceContext = createContext<WorkspaceContextType | undefined>(
  undefined
);

export const useWorkspace = () => {
  const context = useContext(WorkspaceContext);
  if (!context) {
    throw new Error("useWorkspace must be used within a WorkspaceProvider");
  }
  return context;
};

interface WorkspaceProviderProps {
  children: ReactNode;
}

export const WorkspaceProvider: React.FC<WorkspaceProviderProps> = ({
  children,
}) => {
  const [windows, setWindows] = useState<WorkspaceWindow[]>([]);
  const [activeWindowId, setActiveWindowId] = useState<string | null>(null);

  const openWindow = (newWindow: Omit<WorkspaceWindow, "id" | "isActive">) => {
    const id = `window-${Date.now()}-${Math.random()
      .toString(36)
      .substr(2, 9)}`;

    // Verificar se já existe uma janela com o mesmo path
    const existingWindow = windows.find((w) => w.path === newWindow.path);
    if (existingWindow) {
      setActiveWindow(existingWindow.id);
      return;
    }

    const windowWithId: WorkspaceWindow = {
      ...newWindow,
      id,
      isActive: true,
    };

    setWindows((prev) => {
      const updated = prev.map((w) => ({ ...w, isActive: false }));
      return [...updated, windowWithId];
    });

    setActiveWindowId(id);
  };

  const closeWindow = (id: string) => {
    setWindows((prev) => {
      const filtered = prev.filter((w) => w.id !== id);

      // Se fechou a janela ativa, ativar a última
      if (activeWindowId === id && filtered.length > 0) {
        const lastWindow = filtered[filtered.length - 1];
        setActiveWindowId(lastWindow.id);
        return filtered.map((w) => ({
          ...w,
          isActive: w.id === lastWindow.id,
        }));
      }

      if (filtered.length === 0) {
        setActiveWindowId(null);
      }

      return filtered;
    });
  };

  const setActiveWindow = (id: string) => {
    setWindows((prev) =>
      prev.map((w) => ({
        ...w,
        isActive: w.id === id,
      }))
    );
    setActiveWindowId(id);
  };

  const closeAllWindows = () => {
    setWindows([]);
    setActiveWindowId(null);
  };

  return (
    <WorkspaceContext.Provider
      value={{
        windows,
        activeWindowId,
        openWindow,
        closeWindow,
        setActiveWindow,
        closeAllWindows,
      }}
    >
      {children}
    </WorkspaceContext.Provider>
  );
};
