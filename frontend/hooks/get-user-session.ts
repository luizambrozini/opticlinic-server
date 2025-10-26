import { useState, useEffect } from "react";
import Cookies from "js-cookie";

interface UserSession {
  id: string;
  email: string;
  name: string;
  role: string;
}

export const useUserSession = () => {
  const [session, setSession] = useState<UserSession | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const getSessionFromCookie = () => {
      try {
        const sessionCookie = Cookies.get("user-session");
        if (sessionCookie) {
          const parsedSession = JSON.parse(sessionCookie);
          setSession(parsedSession);
        }
      } catch (error) {
        console.error("Error parsing session cookie:", error);
        Cookies.remove("user-session");
      } finally {
        setLoading(false);
      }
    };

    getSessionFromCookie();
  }, []);

  const clearSession = () => {
    Cookies.remove("user-session");
    setSession(null);
  };

  const updateSession = (newSession: UserSession) => {
    Cookies.set("user-session", JSON.stringify(newSession), { expires: 7 });
    setSession(newSession);
  };

  return {
    session,
    loading,
    clearSession,
    updateSession,
    isAuthenticated: !!session,
  };
};
