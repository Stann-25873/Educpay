import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { userService } from "../../services/userService";
import { Card } from "../../components/common/Card";
import { Badge } from "../../components/common/Badge";
import { HiOutlineArrowLeft } from "react-icons/hi";

export function UserDetail() {
  const { id } = useParams();
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    userService.getById(id)
      .then(setUser)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <div className="p-6"><p className="text-gray-500">Chargement...</p></div>;
  if (!user) return (
    <div className="p-6">
      <p className="text-gray-500">Utilisateur introuvable.</p>
      <Link to="/users" className="text-indigo-600 hover:underline mt-2 inline-block">Retour</Link>
    </div>
  );

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center gap-4">
        <Link to="/users" className="text-gray-400 hover:text-gray-600"><HiOutlineArrowLeft className="w-5 h-5" /></Link>
        <div>
          <h1 className="text-2xl font-bold text-gray-900">{user.firstName} {user.lastName}</h1>
          <p className="text-sm text-gray-500">{user.email}</p>
        </div>
        <Badge variant={user.status === "ACTIVE" ? "success" : "danger"} className="ml-auto">{user.status}</Badge>
      </div>
      <Card className="p-5 max-w-md">
        <h3 className="text-sm font-semibold text-gray-700 mb-3">Détails</h3>
        <dl className="space-y-2 text-sm">
          <div className="flex justify-between"><dt className="text-gray-500">Email</dt><dd>{user.email}</dd></div>
          <div className="flex justify-between"><dt className="text-gray-500">Rôle</dt><dd><Badge>{user.roleName || "N/A"}</Badge></dd></div>
          <div className="flex justify-between"><dt className="text-gray-500">Institution</dt><dd>{user.institutionName || "-"}</dd></div>
          <div className="flex justify-between"><dt className="text-gray-500">Statut</dt><dd><Badge variant={user.status === "ACTIVE" ? "success" : "danger"}>{user.status}</Badge></dd></div>
        </dl>
      </Card>
    </div>
  );
}
