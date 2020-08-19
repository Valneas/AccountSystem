# AccountSystem (v0.2 )
The Account System plugin to manage every accounts on the server

## Wiki | Développeurs

---

**PS :** Pour récupérer une instance de la classe `AccountSystem` il suffit de faire ceci :

```java
AccountSystem accountSystem = (AccountSystem) Bukkit.getPluginManager().getPlugin("AccountSystem");
```

---

### Gérer le compte d'un joueur 
Pour récupérer et gérer le compte d'un joueur il vous suffit de créer une nouvelle instance de la classe `AccountManager` et ceci à chaque fois que vous ne disposez pas déjà d'une instance de cette dernière.

Pour en créer une, rien de plus simple :

```java
final AccountManager accountManager = new AccountManager(*instance de AccountSystem*, *Un objet Player*);
```
ou :

```java
final AccountManager accountManager = new AccountManager(*instance de AccountSystem*, *le nom du joueur (String)*, *l'uuid du joueur (String)*);
```

### Gérer les rangs d'un joueur

Pour gérer les d'un joueur il vous faut récupérer une instance de la classe `Rank` pour ceci rien de plus simple, il vous faut au préalable une instance de la classe `AccountSystem` de disponible.

```java
final Rank rank = account.newRankManager();
```

**Attention : Cette méthode CREER une NOUVELLE instance de la classe Rank, vous n'obtiendrez donc pas la même instance à chaque fois que vous appelez cette méthode, c'est bien pour ça que le concept de variable a été inventé :smile:**

## Wiki | Les commandes

### /rank
La seule commande disponible actuellement est la commande /rank, elle permet de set le major rank de quelqu'un, voir ses rangs, lui ajouter un rang et lui en enlever un.

**/rank show [joueur/uuid]** permet de voir les rangs d'un joueur\
**/rank set [joueur/uuid] [id/nom du rang/power]** permet de changer le `rang majeur` d'un joueur\
**/rank add [joueur/uuid] [id/nom du rang/power]** permet d'ajouter un rang fictif à un joueur\
**/rank remove [joueur/uuid] [id/nom du rang/power]** permet de retirer un rang fictif à un joueur

## Wiki | 'super-user'

Le champ super-user est ajouté manuellement à un compte dans la base de données
Il est a été ajouté à 2 utilisateurs jusqu'à présent.

Pour vérifier si un joueur a cette autorisation :

``̀`java
if(accountManager.getAccount().containsKey("super-user") && accountManager.getAccount().getBoolean("super-user")){
  //CODE
}
```

Et pour vérifier s'il ne l'a pas :

```java
if(!accountManager.getAccount().containsKey("super-user") || !accountManager.getAccount().getBoolean("super-user")){
  //CODE
}
```

:warning: Cette version supporte le champ '**super-user**' :warning: 
