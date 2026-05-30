def predict_price(ticker):
    data = yf.download(ticker, period="1y")

    # ❗ agar data empty ho to error na aaye
    if data.empty:
        return None, None, None

    # prediction column
    data['Prediction'] = data['Close'].shift(-1)

    # training data
    X = np.array(data['Close'][:-1]).reshape(-1, 1)
    y = np.array(data['Prediction'][:-1])

    # model
    model = RandomForestRegressor(n_estimators=100)
    model.fit(X, y)

    # prediction
    last_price = np.array([data['Close'].iloc[-1]]).reshape(-1, 1)
    prediction = model.predict(last_price)

    # graph generate
    plt.figure(figsize=(6,4))
    data['Close'].plot(title=ticker)
    plt.tight_layout()

    img = io.BytesIO()
    plt.savefig(img, format='png')
    img.seek(0)
    graph_url = base64.b64encode(img.getvalue()).decode()
    plt.close()

    return round(prediction[0], 2), data, graph_url