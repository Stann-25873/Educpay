import { Link } from "react-router-dom";
import { Button } from "../../components/common/Button";
import { Card } from "../../components/common/Card";
import { HiOutlineAcademicCap, HiOutlineShieldCheck, HiOutlineChartBar, HiOutlineCash } from "react-icons/hi";

const features = [
  {
    icon: HiOutlineAcademicCap,
    title: "Gestion scolaire complète",
    desc: "Étudiants, parents, enseignants et comptables dans un tableau de bord unifié.",
  },
  {
    icon: HiOutlineCash,
    title: "Paiements & facturation",
    desc: "Suivi des frais, génération de factures, encaissement et reçus automatiques.",
  },
  {
    icon: HiOutlineChartBar,
    title: "Rapports financiers",
    desc: "Tableaux de bord et graphiques pour analyser les revenus et les impayés.",
  },
  {
    icon: HiOutlineShieldCheck,
    title: "Sécurité multi-tenant",
    desc: "Isolation stricte des données par établissement. Conforme OWASP Top 10.",
  },
];

const testimonials = [
  { quote: "EduPay a transformé notre gestion financière. Plus besoin de Excel.", author: "Directeur école primaire" },
  { quote: "Les parents peuvent payer en ligne et suivre les factures. Génial.", author: "Comptable lycée" },
  { quote: "L'isolation multi-tenant nous donne une tranquillité d'esprit totale.", author: "CTO groupe scolaire" },
];

export function Landing() {
  return (
    <div className="min-h-screen bg-white">
      {/* Nav */}
      <header className="border-b border-gray-100">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
          <div className="flex items-center gap-2">
            <div className="w-8 h-8 bg-indigo-600 rounded-lg flex items-center justify-center">
              <span className="text-white font-bold text-sm">E</span>
            </div>
            <span className="font-bold text-xl text-gray-900">EduPay</span>
          </div>
          <div className="flex items-center gap-4">
            <Link to="/login" className="text-sm text-gray-600 hover:text-gray-900">Connexion</Link>
            <Link to="/login">
              <Button size="sm">Démo gratuite</Button>
            </Link>
          </div>
        </div>
      </header>

      {/* Hero */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20 md:py-28">
        <div className="text-center max-w-3xl mx-auto">
          <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold text-gray-900 tracking-tight">
            La gestion financière scolaire{" "}
            <span className="text-indigo-600">simplifiée</span>
          </h1>
          <p className="mt-6 text-lg text-gray-600">
            EduPay est la plateforme SaaS multi-tenant qui centralise la facturation, 
            les paiements et le suivi financier de votre établissement.
          </p>
          <div className="mt-10 flex items-center justify-center gap-4">
            <Link to="/login">
              <Button size="lg" className="px-8">Commencer</Button>
            </Link>
            <Link to="/login">
              <Button variant="outline" size="lg" className="px-8">En savoir plus</Button>
            </Link>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="bg-gray-50 py-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <h2 className="text-3xl font-bold text-center text-gray-900 mb-12">
            Tout ce dont vous avez besoin
          </h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
            {features.map((f, i) => (
              <Card key={i} className="p-6 text-center hover:shadow-lg transition-shadow">
                <div className="w-12 h-12 bg-indigo-100 rounded-xl flex items-center justify-center mx-auto mb-4">
                  <f.icon className="w-6 h-6 text-indigo-600" />
                </div>
                <h3 className="font-semibold text-gray-900 mb-2">{f.title}</h3>
                <p className="text-sm text-gray-500">{f.desc}</p>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Testimonials */}
      <section className="py-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <h2 className="text-3xl font-bold text-center text-gray-900 mb-12">
            Ils nous font confiance
          </h2>
          <div className="grid md:grid-cols-3 gap-6">
            {testimonials.map((t, i) => (
              <Card key={i} className="p-6">
                <p className="text-gray-600 italic mb-4">"{t.quote}"</p>
                <p className="text-sm font-medium text-gray-900">— {t.author}</p>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* CTA */}
      <section className="bg-indigo-600 py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <h2 className="text-3xl font-bold text-white mb-4">
            Prêt à simplifier votre gestion ?
          </h2>
          <p className="text-indigo-200 mb-8 max-w-xl mx-auto">
            Rejoignez les établissements qui font confiance à EduPay.
          </p>
          <Link to="/login">
            <Button variant="secondary" size="lg" className="px-10 bg-white text-indigo-600 hover:bg-indigo-50">
              Démarrer maintenant
            </Button>
          </Link>
        </div>
      </section>

      {/* Footer */}
      <footer className="border-t border-gray-100 py-8">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center text-sm text-gray-500">
          &copy; {new Date().getFullYear()} EduPay. Tous droits réservés.
        </div>
      </footer>
    </div>
  );
}