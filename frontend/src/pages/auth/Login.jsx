import { useState } from "react";
import { Link } from "react-router-dom";
import { Card } from "../../components/common/Card";
import { Button } from "../../components/common/Button";

export function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (submitting) return;
    setSubmitting(true);
    setErrorMessage("");
    
    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });
      
      const data = await response.json();

      if (response.ok) {
        // Stockage sécurisé des tokens renvoyés par Spring Boot
        if (data.access_token) {
          localStorage.setItem("token", data.access_token);
        } else if (data.token) {
          localStorage.setItem("token", data.token);
        }
        
        localStorage.setItem("edu_user_session", JSON.stringify(data));
        
        // Redirection forcée vers le dashboard
        window.location.href = "/dashboard";
      } else {
        setErrorMessage(data.message || "Email ou mot de passe incorrect.");
      }
    } catch (err) {
      console.error("Erreur réseau :", err);
      setErrorMessage("Impossible de joindre le serveur.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-indigo-50 to-white px-4">
      <Card className="w-full max-w-md p-8 shadow-xl">
        <div className="text-center mb-8">
          <div className="w-12 h-12 bg-indigo-600 rounded-xl flex items-center justify-center mx-auto mb-4">
            <svg className="w-7 h-7 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4" />
            </svg>
          </div>
          <h1 className="text-2xl font-bold text-gray-900">EduPay</h1>
          <p className="text-sm text-gray-500 mt-1">Plateforme de gestion financière scolaire</p>
        </div>

        {errorMessage && (
          <div className="mb-4 p-3 bg-red-50 border border-red-200 rounded-lg text-sm text-red-700">
            {errorMessage}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
              Email
            </label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition-colors text-sm"
              placeholder="vous@etablissement.fr"
            />
          </div>

          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
              Mot de passe
            </label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition-colors text-sm"
              placeholder="••••••••"
            />
          </div>

          <div className="flex items-center justify-between">
            <label className="flex items-center gap-2 text-sm text-gray-600">
              <input type="checkbox" className="rounded border-gray-300 text-indigo-600 focus:ring-indigo-500" />
              Se souvenir de moi
            </label>
            <Link to="/forgot-password" className="text-sm text-indigo-600 hover:text-indigo-700">
              Mot de passe oublié ?
            </Link>
          </div>

          <Button
            type="submit"
            disabled={submitting}
            className="w-full py-2.5"
          >
            {submitting ? "Connexion..." : "Se connecter"}
          </Button>
        </form>

        <p className="mt-6 text-center text-sm text-gray-500">
          Pas encore de compte ?{" "}
          <Link
            to="/register"
            className="text-indigo-600 hover:text-indigo-700 font-medium"
          >
            Créer un compte
          </Link>
        </p>

        <p className="mt-4 text-center text-xs text-gray-400">
          &copy; {new Date().getFullYear()} EduPay. Tous droits réservés.
        </p>
      </Card>
    </div>
  );
}