package software.ulpgc.moneycalculator.application.queen;

import com.google.gson.reflect.TypeToken;
import software.ulpgc.moneycalculator.architecture.model.Currency;
import com.google.gson.Gson;
import software.ulpgc.moneycalculator.architecture.model.ExchangeRate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String CURRENCIES_FILE = "currencies.json";
    private static final String RATES_FILE = "rates_cache.json";
    private final Gson gson = new com.google.gson.GsonBuilder()
            .registerTypeAdapter(java.time.LocalDate.class, (com.google.gson.JsonSerializer<java.time.LocalDate>)
                    (src, typeOfSrc, context) -> new com.google.gson.JsonPrimitive(src.toString()))
            .registerTypeAdapter(java.time.LocalDate.class, (com.google.gson.JsonDeserializer<java.time.LocalDate>)
                    (json, typeOfT, context) -> java.time.LocalDate.parse(json.getAsString()))
            .create();

    public void saveCurrencies(List<Currency> currencies) {
        try (Writer writer = new FileWriter(CURRENCIES_FILE)) {
            gson.toJson(currencies, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<Currency> loadCurrencies() {
        File file = new File(CURRENCIES_FILE);
        if (!file.exists() || file.length() == 0) return new ArrayList<>(); // Verificación de seguridad

        try (Reader reader = new FileReader(file)) {
            List<Currency> list = gson.fromJson(reader, new TypeToken<List<Currency>>(){}.getType());
            return list != null ? list : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void saveRate(ExchangeRate rate) {
        List<ExchangeRate> rates = loadAllRates();
        rates.removeIf(r -> r.from().code().equals(rate.from().code()) && r.to().code().equals(rate.to().code()));
        rates.add(rate);
        try (Writer writer = new FileWriter(RATES_FILE)) {
            gson.toJson(rates, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<ExchangeRate> loadAllRates() {
        File file = new File(RATES_FILE);
        if (!file.exists() || file.length() == 0) return new ArrayList<>(); // Verificación de seguridad

        try (Reader reader = new FileReader(file)) {
            List<ExchangeRate> list = gson.fromJson(reader, new TypeToken<List<ExchangeRate>>() {
            }.getType());
            return list != null ? list : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
