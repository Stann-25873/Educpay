import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { parentService } from "../../services/parentService";
import { Card } from "../../components/common/Card";
import { Badge } from "../../components/common/Badge";
import { Button } from "../../components/common/Button";
import { HiOutlineArrowLeft, HiOutlinePencil } from "react-icons/hi";

export function ParentDetail() {
  const { id } = useParams();
  const [parent, setParent] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    parentService.getById(id).then(setParent).catch(console.error).finally(() => setLoading(false));
  }, [id]);

  if (loading) return <div className="p-6"><p className="text-gray-500">Chargement...</p></div>;
  if (!parent) return (
    <div className="p-6">
      <p className="text-gray-500">Parent introuvable.</p>
      <Link to="/parents" className="text-indigo-600 hover:underline mt-2 inline-block">Retour</Link>
    </div>
  );

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center gap-4">
        <Link to="/parents" className="text-gray-400 hover:text-gray-600"><HiOutlineArrowLeft className="w-5 h-5" /></Link>
        <div>
          <h1 className="text-2xl font-bold text-gray-900">{parent.firstName} {parent.lastName}</h1>
          <p className="text-sm text-gray-500">{parent.email}</p>
        </div>
        <div className="ml-auto"><Button variant="outline" className="flex items-center gap-1"><HiOutlinePencil className="w-4 h-4" /> Modifier</Button></div>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card className="p-5">
          <h3 className="text-sm font-semibold text-gray-700 mb-3">Informations</h3>
          <dl className="space-y-2 text-sm">
            <div className="flex justify-between"><dt className="text-gray-500">Email</dt><dd>{parent.email || "-"}</dd></div>
            <div className="flex justify-between"><dt className="text-gray-500">Téléphone</dt><dd>{parent.phone || "-"}</dd></div>
            <div className="flex justify-between"><dt className="text-gray-500">Institution</dt><dd>{parent.institutionName || "-"}</dd></div>
          </dl>
        </Card>
        <Card className="p-5">
          <h3 className="text-sm font-semibold text-gray-700 mb-3">Étudiants liés</h3>
          {parent.students?.length ? (
            <ul className="divide-y divide-gray-100">
              {parent.students.map((s) => (
                <li key={s.id} className="py-2 flex items-center gap-3">
                  <div className="w-8 h-8 bg-indigo-100 rounded-full flex items-center justify-center text-xs font-semibold text-indigo-600">
                    {s.firstName?.[0]}{s.lastName?.[0]}
                  </div>
                  <div><p className="text-sm font-medium">{s.firstName} {s.lastName}</p><p className="text-xs text-gray-500">{s.level || "N/A"}</p></div>
                </li>
              ))}
            </ul>
          ) : <p className="text-sm text-gray-400">Aucun étudiant lié</p>}
        </Card>
      </div>
    </div>
  );
}