# language: fr
Fonctionnalité: Assigner un siège

  En tant que compagnie aérienne
  Je veux pouvoir assigner un siège à un passager ayant une réservation à bord d'un vol

@niveau=large @happypath @risque @strategy=ui
Scénario: Assigner un siège dans un vol ayant des places disponibles avec le mode aléatoire
    Étant donné un passage Alice ayant une réservation à bord d'un vol
    Et que des places sont disponibles sur ce vol
    Et qu'elle a fait son checkin
    Quand elle procède à l'assignation de siège en choisissant le mode aléatoire
    Alors un siège lui a été assigné à bord de ce vol
