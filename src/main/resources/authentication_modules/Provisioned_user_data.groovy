package authentication_modules

import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder

import javax.crypto.Cipher
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

public static PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
    byte[] clear = new BASE64Decoder().decodeBuffer(key64);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
    KeyFactory fact = KeyFactory.getInstance("RSA");
    PrivateKey priv = fact.generatePrivate(keySpec);
    Arrays.fill(clear, (byte) 0);
    return priv;
}


public static PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
    byte[] data = new BASE64Decoder().decodeBuffer(stored);
    X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
    KeyFactory fact = KeyFactory.getInstance("RSA");
    return fact.generatePublic(spec);
}

public static String savePrivateKey(PrivateKey priv) throws GeneralSecurityException {
    KeyFactory fact = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec spec = fact.getKeySpec(priv,
            PKCS8EncodedKeySpec.class);
    byte[] packed = spec.getEncoded();
    String key64 = new BASE64Encoder().encode(packed);

    Arrays.fill(packed, (byte) 0);
    return key64;
}


public static String savePublicKey(PublicKey publ) throws GeneralSecurityException {
    KeyFactory fact = KeyFactory.getInstance("RSA");
    X509EncodedKeySpec spec = fact.getKeySpec(publ,
            X509EncodedKeySpec.class);
    return new BASE64Encoder().encode(spec.getEncoded());
}


public static void main(String[] args) throws Exception {
    KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
    KeyPair pair = gen.generateKeyPair();

    String pubKey = savePublicKey(pair.getPublic());
    PublicKey pubSaved = loadPublicKey(pubKey);
    System.out.println(pair.getPublic() + "\n" + pubSaved);

    String privKey = savePrivateKey(pair.getPrivate());
    PrivateKey privSaved = loadPrivateKey(privKey);
    System.out.println(pair.getPrivate() + "\n" + privSaved);
}

public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
    final int keySize = 2048;
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(keySize);
    return keyPairGenerator.genKeyPair();
}

public static Boolean verify(PublicKey publicKey, byte[] bytes, String data) throws Exception {
    Signature signature = Signature.getInstance("SHA512withRSA");
    signature.initVerify(publicKey);
    signature.update(data.getBytes("US-ASCII"))
    return signature.verify(bytes)
}

public static byte[] sign(PrivateKey privateKey, String message) throws Exception {
    Signature signature = Signature.getInstance("SHA512withRSA");
    signature.initSign(privateKey);
    signature.update(message.getBytes("US-ASCII"))
    return signature.sign()
}

savePrivateKey(buildKeyPair().getPrivate())

System.out.println(this.getClass().getSimpleName())

def io_user_authentication = binding.getVariable("io_user_authentication")

if (io_user_authentication.publicDataFieldSet == null) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.publicDataFieldSet.get("provisioned_data_unique_id") == null ||
        io_user_authentication.publicDataFieldSet.get("proxy_number") == null ||
        io_user_authentication.publicDataFieldSet.get("provisioned_user_data_usage_authorization") == null ||
        io_user_authentication.privateDataFieldSet.get("provisioned_data_signature") == null) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.p_parent_authorization.is_invalid_access_jwt(io_user_authentication.publicDataFieldSet.get("provisioned_user_data_usage_authorization") as String, io_user_authentication.p_context)) {
    io_user_authentication.failure()
    return
}

def l_provisioned_user_data_usage_authorization = io_user_authentication.p_parent_authorization.access_jwt2authorization(io_user_authentication.publicDataFieldSet.get("provisioned_user_data_usage_authorization") as String, io_user_authentication.p_context)

if (l_provisioned_user_data_usage_authorization.expiryDate.before(new Date())) {
    io_user_authentication.failure()
    return
}

if (!l_provisioned_user_data_usage_authorization.scope.keyFieldMap.containsKey("proxy_number")) {
    io_user_authentication.failure()
    return
}

if (l_provisioned_user_data_usage_authorization.scope.keyFieldMap.get("proxy_number") != io_user_authentication.publicDataFieldSet.get("proxy_number")) {
    io_user_authentication.failure()
    return
}

Boolean is_valid_signature = verify(
        loadPublicKey(
                l_provisioned_user_data_usage_authorization.functionalFieldMap.get("provisioning_public_key") as String
        ),
        new BASE64Decoder().decodeBuffer(io_user_authentication.privateDataFieldSet.get("provisioned_data_signature") as String),
        (io_user_authentication.publicDataFieldSet.get("proxy_number") + io_user_authentication.publicDataFieldSet.get("provisioned_data_unique_id")) as String
)

//String l_expected_check = (io_user_authentication.publicDataFieldSet.get("proxy_number") + io_user_authentication.publicDataFieldSet.get("provisioned_data_unique_id"))

//System.out.println(l_decrypted_sig)
//System.out.println(l_expected_check)

if (!is_valid_signature) {
    io_user_authentication.failure()
    return
}

io_user_authentication.keyFieldMap = new HashMap<String, String>()
io_user_authentication.functionalFieldMap = new HashMap<String, String>()
io_user_authentication.keyFieldMap.put("proxy_number", io_user_authentication.publicDataFieldSet.get("proxy_number") as String)
io_user_authentication.success()