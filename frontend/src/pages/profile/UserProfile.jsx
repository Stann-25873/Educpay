import { useState } from "react";
import { useAuth } from "../../hooks/useAuth";
import { Card } from "../../components/common/Card";
import { Button } from "../../components/common/Button";
import { Badge } from "../../components/common/Badge";
import { HiOutlineUser } from "react-icons/hi";

export function UserProfile() {
  const { user, logout } = useAuth();

  return (
    <div className="p-6 max-w-xl mx-auto space-y-6">
      <div className="flex items-center gap-4">
        <div className="w-16 h-16 bg-indigo-100 rounded-full flex items-center justify-center">
          <HiOutlineUser className="w-8 h-8 text-indigo-600" />
        </div>
        <div>
          <h1 className="text-2xl font-bold text-gray-900">
            {user?.firstName} {user?.lastName}
          </h1>
          <p className="text-sm text-gray-500">{user?.email}</p>
        </div>
        <Badge variant="success" className="ml-auto">{user?.status || "ACTIVE"}</Badge>
      </div>

      <Card className="p-5">
        <h3 className="text-sm font-semibold text-gray-700 mb-3">Informations du compte</h3>
        <dl className="space-y-3 text-sm">
          <div className="flex justify-between"><dt className="text-gray-500">Email</dt><dd>{user?.email || "-"}</dd></div>
          <div className="flex justify-between"><dt className="text-gray-500">Rôle</dt><dd><Badge>{user?.roleName || user?.roles?.[0] || "N/A"}</Badge></dd></div>
          <div className="flex justify-between"><dt className="text-gray-500">Institution</dt><dd>{user?.institutionName || "-"}</dd></div>
          <div className="flex justify-between"><dt className="text-gray-500">Statut</dt><dd><Badge variant="success">{user?.status || "ACTIVE"}</Badge></dd></div>
        </dl>
      </Card>

      <Card className="p-5">
        <h3 className="text-sm font-semibold text-gray-700 mb-3">Actions</h3>
        <Button variant="danger" onClick={logout} className="w-full">
          Se déconnecter
        </Button>
      </Card>
    </div>
  );
}