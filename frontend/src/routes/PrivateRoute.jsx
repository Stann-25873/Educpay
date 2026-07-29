import { Navigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

export function PrivateRoute({ children }) {
  const { isAuthenticated } = useAuth();
  
  // Double vérification directe avec le localStorage pour éviter tout blocage synchrone
  const hasToken = typeof window !== "undefined" && Boolean(localStorage.getItem("token") || localStorage.getItem("edu_user_session"));

  if (!isAuthenticated && !hasToken) {
    return <Navigate to="/login" replace />;
  }
  
  return children;
}