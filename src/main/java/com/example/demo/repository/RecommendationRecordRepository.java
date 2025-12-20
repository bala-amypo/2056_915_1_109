@Entity
public class RecommendationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long purchaseIntentId;
    private Long recommendedCardId;
    private double expectedRewardValue;

    @Column(columnDefinition = "TEXT")
    private String calculationDetailsJson;

    // ===== GETTERS & SETTERS =====
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRecommendedCardId() {
        return recommendedCardId;
    }

    public void setRecommendedCardId(Long recommendedCardId) {
        this.recommendedCardId = recommendedCardId;
    }

    public void setPurchaseIntentId(Long purchaseIntentId) {
        this.purchaseIntentId = purchaseIntentId;
    }

    public void setExpectedRewardValue(double expectedRewardValue) {
        this.expectedRewardValue = expectedRewardValue;
    }

    public void setCalculationDetailsJson(String calculationDetailsJson) {
        this.calculationDetailsJson = calculationDetailsJson;
    }
}