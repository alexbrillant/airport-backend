# language: fr
Fonctionnalité: Enregistrer un bagage

  En tant que compagnie aérienne
  Je veux pouvoir enregistrer les bagages des passagers.
  Je veux aussi pouvoir calculer les prix associés aux bagages.

@niveau=medium @strategy=application
Scénario:  Enregistrer un bagage
  Étant donné un passager Bob ayant une réservation en classe économique sur le vol AC-1234
  Et qu'il a déjà un bagage enregistré respectant les normes
  Quand il enregistre le bagage suivant :
  | type    | poids | taille |
  | checked | 10kg  | 100cm  |
  Alors son total a payer est de 50$