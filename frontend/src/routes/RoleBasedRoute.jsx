import { Navigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

export function RoleBasedRoute({ roles, children }) {
  const { roles: userRoles } = useAuth();
  const allowed = roles.some((r) => userRoles?.includes(r));

  if (!allowed) return <Navigate to="/" replace />;
  return children;
}

