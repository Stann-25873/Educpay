import { useState, useEffect } from "react";
import { useAuth } from "../../hooks/useAuth";
import { Card } from "../../components/common/Card";
import { Button } from "../../components/common/Button";
import { HiOutlineCog } from "react-icons/hi";

export function InstitutionSettings() {
  const { user } = useAuth();
  const [saving, setSaving] = useState(false);
  const [form, setForm] = useState({
    name: user?.institutionName || "",
    type: user?.institutionType || "SCHOOL",
    email: "",
    phone: "",
    address: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      // Simulate save
      await new Promise((r) => setTimeout(r, 500));
      alert("Paramètres sauvegardés");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="p-6 max-w-2xl mx-auto space-y-6">
      <div className="flex items-center gap-3">
        <HiOutlineCog className="w-8 h-8 text-indigo-600" />
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Paramètres</h1>
          <p className="text-sm text-gray-500 mt-1">Configuration de l'établissement</p>
        </div>

      <Card className="p-5">
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Nom de l'établissement</label>
            <input type="text" value={form.name} onChange={(e) => setForm({...form, name: e.target.value})}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none text-sm" />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Type d'établissement</label>
            <select value={form.type} onChange={(e) => setForm({...form, type: e.target.value})}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none text-sm">
              <option value="SCHOOL">École</option>
              <option value="UNIVERSITY">Université</option>
              <option value="TRAINING_CENTER">Centre de formation</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Email de contact</label>
            <input type="email" value={form.email} onChange={(e) => setForm({...form, email: e.target.value})}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none text-sm" />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Téléphone</label>
            <input type="tel" value={form.phone} onChange={(e) => setForm({...form, phone: e.target.value})}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none text-sm" />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Adresse</label>
            <textarea value={form.address} onChange={(e) => setForm({...form, address: e.target.value})}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none text-sm" rows="3" />
          </div>
          <Button type="submit" disabled={saving} className="w-full">
            {saving ? "Sauvegarde..." : "Sauvegarder"}
          </Button>
        </form>
      </Card>
    </div>
    </div>
  );
}