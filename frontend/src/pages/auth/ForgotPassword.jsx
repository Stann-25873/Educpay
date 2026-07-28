import { useState } from "react";
import { Link } from "react-router-dom";
import { Card } from "../../components/common/Card";
import { Button } from "../../components/common/Button";

export function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [sent, setSent] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    try {
      // Simulate sending reset email
      await new Promise((r) => setTimeout(r, 1000));
      setSent(true);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-indigo-50 to-white px-4">
      <Card className="w-full max-w-md p-8 shadow-xl">
        {sent ? (
          <div className="text-center">
            <div className="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <svg className="w-6 h-6 text-green-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
            </div>
            <h2 className="text-xl font-bold text-gray-900 mb-2">Email envoyé</h2>
            <p className="text-sm text-gray-500 mb-6">
              Un email de réinitialisation a été envoyé à {email}.
            </p>
            <Link to="/login">
              <Button variant="outline">Retour à la connexion</Button>
            </Link>
          </div>
        ) : (
          <>
            <div className="text-center mb-8">
              <div className="w-12 h-12 bg-indigo-600 rounded-xl flex items-center justify-center mx-auto mb-4">
                <svg className="w-6 h-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z" />
                </svg>
              </div>
              <h1 className="text-2xl font-bold text-gray-900">Mot de passe oublié</h1>
              <p className="text-sm text-gray-500 mt-1">
                Saisissez votre email pour recevoir un lien de réinitialisation
              </p>
            </div>
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
              <Button type="submit" disabled={submitting} className="w-full py-2.5">
                {submitting ? "Envoi..." : "Envoyer le lien"}
              </Button>
            </form>
            <p className="mt-6 text-center">
              <Link to="/login" className="text-sm text-indigo-600 hover:text-indigo-700">
                Retour à la connexion
              </Link>
            </p>
          </>
        )}
      </Card>
    </div>
  );
}