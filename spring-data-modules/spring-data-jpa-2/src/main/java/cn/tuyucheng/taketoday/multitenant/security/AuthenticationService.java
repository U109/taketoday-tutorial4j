package cn.tuyucheng.taketoday.multitenant.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Date;

public class AuthenticationService {

   private static final long EXPIRATIONTIME = 864_000_00; // 1 day in milliseconds
   private static final String SIGNINGKEY = "SecretKey";
   private static final String PREFIX = "Bearer";

   public static void addToken(HttpServletResponse res, String username, String tenant) {
      String JwtToken = Jwts.builder().setSubject(username)
            .setAudience(tenant)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
            .signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
            .compact();
      res.addHeader("Authorization", PREFIX + " " + JwtToken);
   }

   public static Authentication getAuthentication(HttpServletRequest req) {
      String token = req.getHeader("Authorization");
      if (token != null) {
         String user = Jwts.parser()
               .setSigningKey(SIGNINGKEY)
               .parseClaimsJws(token.replace(PREFIX, ""))
               .getBody()
               .getSubject();
         if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
         }
      }

      return null;
   }

   public static String getTenant(HttpServletRequest req) {
      String token = req.getHeader("Authorization");
      if (token == null) {
         return null;
      }
      return Jwts.parser()
            .setSigningKey(SIGNINGKEY)
            .parseClaimsJws(token.replace(PREFIX, ""))
            .getBody()
            .getAudience();
   }
}