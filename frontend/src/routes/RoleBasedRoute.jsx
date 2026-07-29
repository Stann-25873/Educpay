import { Navigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

export function RoleBasedRoute({ roles, children }) {
  const { roles: userRoles } = useAuth();
  const hasToken = typeof window !== "undefined" && Boolean(localStorage.getItem("token") || localStorage.getItem("edu_user_session"));

  // Si l'utilisateur a un token valide mais que les rôles ne sont pas encore chargés, on le laisse passer par sécurité
  const allowed = (roles && roles.length > 0) ? roles.some((r) => userRoles?.includes(r)) : true;

  if (!allowed && !hasToken) {
    return <Navigate to="/" replace />;
  }
  
  return children;
}