export function createHistoryObserver(pathRegexes) {
  return function onHistoryEvent(historyEvent) {
    pathRegexes.forEach(({ regex, callback }) => {
      const matches = regex.exec(historyEvent.pathname)
      if (matches) {
        /*eslint no-unused-vars: ["error", { "varsIgnorePattern": "IGNORED" }]*/
        const [IGNORED, ...captures] = matches
        callback(captures)
      }
    })
  }
}
