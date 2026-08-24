# Fix Notes — 46 Failures ka Solution

## Maine kya fix kiya (actual code bug):
`GetRequestTest.java` mein `verifyGetUsersStatusCode()` test `.spec(requestSpec)`
use nahi kar raha tha, isliye wo base URI (`https://jsonplaceholder.typicode.com`)
attach nahi karta tha aur galat/empty URL pe hit hota tha. Fix kar diya:

```java
RequestSpecification request = RestAssured.given().spec(requestSpec);
```

## IMPORTANT — Ye maine fix NAHI kiya, tumhe Eclipse mein karna hoga:

### Wajah #1: Maven dependencies sync nahi hain (biggest cause of 46 failures)
Tests jo turant fail ho rahe the (0.005s, 0.01s) — matlab network call tak
pahunche hi nahi. Ye classpath issue hai. Fix:

1. Eclipse mein project pe right-click
2. `Maven` → `Update Project...` (ya `Alt+F5`)
3. `Force Update of Snapshots/Releases` checkbox tick karo
4. `OK` — wait karo jab tak "Building workspace" khatam na ho
5. Agar phir bhi laal cross (X) dikhe kisi file pe: right-click project →
   `Maven` → `Update Project` dobara, ya Eclipse restart karo

Agar internet slow hai ya first time hai, ye dependencies download karne mein
2-5 minute lag sakte hain (rest-assured, selenium, cucumber, jackson, etc.)

### Wajah #2: UI tests real live website pe depend karte hain
`HomePageTest` aur `HostingPlansTest` asli `inmotionhosting.com` website ke
CSS/XPath selectors use karte hain (jaise `a.imh-logo`, `ul.nav1 li.nav-item`).
Agar website ka HTML thoda bhi badal gaya ho current date se, ye tests fail
honge — ye code ka bug nahi hai, balki website ki current state se locators
ka mismatch hai. Isko fix karne ke liye:
- Chrome mein `inmotionhosting.com` khol ke DevTools (F12) se actual current
  selectors check karne padenge, phir `HomePage.java` / `HostingPage.java`
  mein locators update karne padenge.

## Run karne ka sabse simple tareeka:
1. Project → right-click → `Maven` → `Update Project` (upar wala step)
2. `testng.xml` pe right-click → `Run As` → `TestNG Suite`
3. Pehle sirf `ApiTests` ya `SmokeTests` chalao (chhota scope) taaki dependency
   issue clear ho jaye, phir poora suite chalao

## Agar abhi bhi failures aayein:
"Failed Tests" tab mein jaake ek test kholo, "Failure Exception" panel mein
poora stack trace dikhega — wahi asli reason hota hai. Screenshot bhej dena,
main exact batा dunga.
